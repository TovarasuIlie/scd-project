using Microsoft.AspNetCore.Components.Authorization;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using UI.AuthenticationProvider;
using UI.Models;

namespace UI.Services
{
    public interface IDeliveryService
    {
        Task<List<Delivery>> GetAllDeliveryAsync();

        Task<List<Delivery>> GetAllMyDeliveryAsync();
        Task<Delivery> GetDeliveryByAWB(string awb);
        Task SetCourierToDelivery(string awb, int id);
    }
    public class DeliveryService : IDeliveryService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly AuthenticationStateProvider _authenticationStateProvider;

        public DeliveryService(IHttpClientFactory httpClientFactory, AuthenticationStateProvider authenticationStateProvider)
        {
            _httpClientFactory = httpClientFactory;
            _authenticationStateProvider = authenticationStateProvider;
        }

        public async Task<List<Delivery>> GetAllDeliveryAsync()
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Delivery/get-all-deliveries");
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<List<Delivery>>();
            }
            return new List<Delivery>();
        }

        public async Task<List<Delivery>> GetAllMyDeliveryAsync()
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Delivery/get-my-deliveries");
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<List<Delivery>>();
            }
            return new List<Delivery>();
        }

        public async Task<Delivery> GetDeliveryByAWB(string awb)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Delivery/get-delivery/" + awb);
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<Delivery>();
            }
            return new Delivery();
        }

        public async Task SetCourierToDelivery(string awb, int id)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Delivery/set-courier-to-delivery/" + awb + "/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status cont!", response.Message, "OK");
        }
    }
}
