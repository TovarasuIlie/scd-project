using System.Text.Json.Serialization;
using UI.Models;

namespace UI.Services
{
    public class Delivery
    {
        [JsonPropertyName("awb")]
        public string AWB { get; set; }

        [JsonPropertyName("pickingAddress")]
        public string PickingAddress { get; set; }

        [JsonPropertyName("packageSet")]
        public List<Package> PackageList { get; set; } = new List<Package> { };

        [JsonPropertyName("courier")]
        public Courier Courier { get; set; }

        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("phoneNumber")]
        public string PhoneNumber { get; set; }

        [JsonPropertyName("specialKey")]
        public string SpecialKey { get; set; }

        [JsonPropertyName("status")]
        public string Status { get; set; }

        [JsonPropertyName("createDate")]
        public DateTime CreateDate { get; set; }

        [JsonPropertyName("price")]
        public decimal Price { get; set; }

    }
}
