﻿using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Threading.Tasks;
using UI.AuthenticationProvider;
using UI.Models;

namespace UI.Services
{
    public interface IDeliveryService
    {
        Task<List<Delivery>> GetAllDeliveryAsync();
        Task<List<Delivery>> GetAllDeliveryForCourierAsync(int id);
        Task<List<Delivery>> GetAllMyDeliveryAsync();
        Task<Delivery> GetDeliveryByAWB(string awb);
        Task SetCourierToDelivery(string awb, int id, NavigationManager navigation);
        Task ChangeDeliveryStatusAsync(Delivery delivery, string status, NavigationManager navigation);
        Task RemoveCourierAsync(string awb, NavigationManager navigation);
        Task DeleteDelivery(string awb, NavigationManager navigation);
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

        public async Task SetCourierToDelivery(string awb, int id, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsync("/api/Delivery/set-courier-to-delivery/" + awb + "/" + id, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status livrare!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo(navigation.Uri, true);
            }
        }

        public async Task ChangeDeliveryStatusAsync(Delivery delivery, string status, NavigationManager navigation)
        {
            delivery.Status = status;
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsJsonAsync<Delivery>("/api/Delivery/set-delivery-status", delivery);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status livrare!", response.Message, "OK");
            if(result.IsSuccessStatusCode)
            {
                navigation.NavigateTo(navigation.Uri, true);
            }
        }

        public async Task RemoveCourierAsync(string awb, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.PutAsJsonAsync<Delivery>("/api/Delivery/remove-courier/" + awb, null);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status livrare!", response.Message, "OK");
            if(result.IsSuccessStatusCode)
            {
                navigation.NavigateTo(navigation.Uri, true);
            }
        }

        public async Task DeleteDelivery(string awb, NavigationManager navigation)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var result = await httpClient.DeleteAsync("/api/Delivery/delete-delivery/" + awb);
            var response = await result.Content.ReadFromJsonAsync<API>();
            await Application.Current.MainPage.DisplayAlert("Status livrare!", response.Message, "OK");
            if (result.IsSuccessStatusCode)
            {
                navigation.NavigateTo("/deliveries", true);
            }
        }

        public async Task<List<Delivery>> GetAllDeliveryForCourierAsync(int id)
        {
            var httpClient = _httpClientFactory.CreateClient("API");
            var customAuthStateProvider = (CustomAuthenticationStateProvider)_authenticationStateProvider;
            var currentUser = await customAuthStateProvider.GetAuthenticationStateAsync();
            httpClient.DefaultRequestHeaders.Authorization = new AuthenticationHeaderValue("Bearer", currentUser.User.Claims.First(x => x.Type == "Jwt").Value);
            var apiResponse = await httpClient.GetAsync("/api/Delivery/get-delivery-for/" + id);
            if (apiResponse.IsSuccessStatusCode)
            {
                return await apiResponse.Content.ReadFromJsonAsync<List<Delivery>>();
            }
            return new List<Delivery>();
        }
    }
}
