using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.Maui.Controls;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text;
using System.Threading.Tasks;
using UI.AuthenticationProvider;
using UI.Models;

namespace UI.Services
{
    public interface IPackageService
    {
        Task ChangePackageStatus(Package package, string status, NavigationManager navigation);
    }
    public class PackageService : IPackageService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly AuthenticationStateProvider _authenticationStateProvider;

        public PackageService(IHttpClientFactory httpClientFactory, AuthenticationStateProvider authenticationStateProvider)
        {
            _httpClientFactory = httpClientFactory;
            _authenticationStateProvider = authenticationStateProvider;
        }

        public async Task ChangePackageStatus(Package package, string status, NavigationManager navigation)
        {
            package.Status = status;
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsJsonAsync<Package>("/api/Package/change-package-status", package);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
        }
    }
}
