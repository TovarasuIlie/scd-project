using System.Net.Http.Json;
using System.Text.Json;
using UI.DTOs;
using UI.Pages;

namespace UI.Services
{
    public class AuthService
    {
        private readonly IHttpClientFactory _httpClientFactory;

        public AuthService(IHttpClientFactory httpClientFactory)
        {
            _httpClientFactory = httpClientFactory;
        }
        public async Task<bool> isUserAuthenticated()
        {
            return true;
        }

        public async Task RegisterAsync(RegisterRequestDTO registerDTO)
        {
            var httpClient = _httpClientFactory.CreateClient("custom-httpclient");
            var result = await httpClient.PostAsJsonAsync("/api/Authentification/register-account", registerDTO);
            if(result.IsSuccessStatusCode)
            {
                await Shell.Current.DisplayAlert("Alert", "Cont Creat", "OK");
            }
            await Shell.Current.DisplayAlert("Alert", result.ReasonPhrase, "OK");
        }

        public async Task LoginAsync(LoginRequestDTO loginDTO)
        {
            if(!string.IsNullOrEmpty(loginDTO.Username) && !string.IsNullOrEmpty(loginDTO.Password))
            {
                if(IsValidEmail(loginDTO.Username)) {
                    var httpClient = _httpClientFactory.CreateClient("custom-httpclient");
                    var result = await httpClient.PostAsJsonAsync("/api/Authentification/login-into", loginDTO);
                    if (result.IsSuccessStatusCode)
                    {
                        var response = await result.Content.ReadFromJsonAsync<AuthResponseDTO>();
                        //var serializeResponse = JsonSerializer.Serialize(
                        //    new AuthResponseDTO() { AccessToken = response.AccessToken, RefreshToken = response.RefreshToken, UserName = loginDTO.Username }
                        //    );
                        //await SecureStorage.Default.SetAsync("Auth", serializeResponse);
                        await Shell.Current.GoToAsync($"//{nameof(IndexPage)}");
                    }
                    else
                    {
                        var response = await result.Content.ReadFromJsonAsync<APIMessages>();
                        await Shell.Current.DisplayAlert("Eroare!", response.message, "OK");
                    }
                }
                else
                {
                    await Shell.Current.DisplayAlert("Eroare!", "Email-ul nu este unul valid!", "OK");
                }
            } 
            else
            {
                await Shell.Current.DisplayAlert("Eroare!", "Toate campurile trebuie completate!", "OK");
            }
        }

        private bool IsValidEmail(string email)
        {
            var trimmedEmail = email.Trim();

            if (trimmedEmail.EndsWith("."))
            {
                return false;
            }
            try
            {
                var addr = new System.Net.Mail.MailAddress(email);
                return addr.Address == trimmedEmail;
            }
            catch
            {
                return false;
            }
        }
    }
}
