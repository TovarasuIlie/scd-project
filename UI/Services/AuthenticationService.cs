using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using System.IdentityModel.Tokens.Jwt;
using System.Net.Http.Json;
using UI.AuthenticationProvider;
using UI.Models;
using UI.Models.Authentication;
using UI.Models.FormModels;

namespace UI.Services
{
    public interface IAuthenticationService
    {
        Task LoginAsync(LoginForm loginForm, NavigationManager navigation);
        Task LogoutAsync(NavigationManager navigation);
        Task RegisterAsync(RegisterForm registerForm, NavigationManager navigation);
    }

    public class AuthenticationService : IAuthenticationService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly AuthenticationStateProvider _authenticationStateProvider;

        public AuthenticationService(IHttpClientFactory httpClientFactory, AuthenticationStateProvider authenticationStateProvider)
        {
            _httpClientFactory = httpClientFactory;
            _authenticationStateProvider = authenticationStateProvider;
        }
        public async Task LoginAsync(LoginForm loginForm, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var result = await httpClient.PostAsJsonAsync("/api/Authentication/login-account", loginForm);
            if (result.IsSuccessStatusCode)
            {
                var response = await result.Content.ReadFromJsonAsync<LoggedUser>();
                var getLoggedUser = GetUserClaims(response.Jwt);
                if (getLoggedUser == null) { return; }
                var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
                await customAuthStateProvider.UpdateAuthenticationStateAsync(getLoggedUser);
                navigation.NavigateTo("/", true);
            }
            else
            {
                var response = await result.Content.ReadFromJsonAsync<API>();
                await Application.Current.MainPage.DisplayAlert("Eroare!", response.Message, "OK");
            }
        }

        public async Task LogoutAsync(NavigationManager navigation)
        {
            navigation.NavigateTo("/login", true);
            var customAuthStateProvide = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            await customAuthStateProvide.UpdateAuthenticationStateAsync(new LoggedUser());
        }

        public async Task RegisterAsync(RegisterForm registerForm, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var result = await httpClient.PostAsJsonAsync("/api/Authentication/register-account", registerForm);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
            if(result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/login", true);
            }
        }

        private LoggedUser GetUserClaims(string jwt)
        {
            var handler = new JwtSecurityTokenHandler();
            var readToken = handler.ReadJwtToken(jwt);
            var claims = readToken.Claims;

            return new LoggedUser()
            {
                Id = Int32.Parse(claims.First(_ => _.Type == "id").Value),
                Email = claims.First(_ => _.Type == "email").Value,
                Name = claims.First(_ => _.Type == "name").Value,
                Role = claims.First(_ => _.Type == "role").Value,
                Jwt = jwt
            };
        }
    }
}
