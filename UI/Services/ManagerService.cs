using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using UI.AuthenticationProvider;
using UI.Models;

namespace UI.Services
{
    public interface IManagerService
    {
        Task<List<Courier>> GetCouriersAsync();
        Task<List<Courier>> GetAllCouriersAsync();
        Task<Courier> GetCourierAsync(int ID);
        Task PromoteToManager(int id, NavigationManager navigation);
        Task PromoteToCourier(int id, NavigationManager navigation);
        Task SetManager(int id, NavigationManager navigation);
        Task RemoveManager(int id, NavigationManager navigation);
    }
    public class ManagerService : IManagerService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly AuthenticationStateProvider _authenticationStateProvider;

        public ManagerService(IHttpClientFactory httpClientFactory, AuthenticationStateProvider authenticationStateProvider)
        {
            _httpClientFactory = httpClientFactory;
            _authenticationStateProvider = authenticationStateProvider;
        }

        public async Task<List<Courier>> GetAllCouriersAsync()
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Manager/get-all-couriers");
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<List<Courier>>();
            }
            return new List<Courier>();
        }

        public async Task<Courier> GetCourierAsync(int ID)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Manager/get-courier/" + ID);
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<Courier>();
            }
            return new Courier();
        }

        public async Task<List<Courier>> GetCouriersAsync()
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Manager/get-all");
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<List<Courier>>();
            }
            return new List<Courier>();
        }

        public async Task PromoteToCourier(int id, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Manager/make-courier/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/couriers/view/" + id, true);
            }
        }

        public async Task PromoteToManager(int id, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Manager/make-manager/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/couriers/view/" + id, true);
            }
        }

        public async Task RemoveManager(int id, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Manager/remove-you-manager/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/couriers/view/" + id, true);
            }
        }

        public async Task SetManager(int id, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Manager/set-you-manager/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/couriers/view/" + id, true);
            }
        }
    }
}
