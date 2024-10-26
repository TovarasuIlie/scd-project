using System.Net.Http.Json;
using System.Text.Json;
using UI.Models;

namespace UI.Services
{
    public class PackageService
    {
        private readonly IHttpClientFactory _httpClientFactory;
        public PackageService(IHttpClientFactory httpClientFactory)
        {
            _httpClientFactory = httpClientFactory;
        }

        public async Task<IEnumerable<Package>> GetPackages()
        {
            var httpClient = _httpClientFactory.CreateClient("custom-httpclient");
            var response = await httpClient.GetAsync("/api/Package/get-all-deliveries");

            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<IEnumerable<Package>>(await response.Content.ReadAsStringAsync());
            }
            else
            {
                return new List<Package>();
            }
        }

        public async Task<Package> GetDelivaryByID(int id)
        {
            var httpClient = _httpClientFactory.CreateClient("custom-httpclient");
            var response = await httpClient.GetAsync("/api/Package/get-delivery-by-id/" + id);
            if (response.IsSuccessStatusCode)
            {
                return JsonSerializer.Deserialize<Package>(await response.Content.ReadAsStringAsync());
            }
            else
            {
                return new Package();
            }
        }
    }
}
