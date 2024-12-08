using System.Text.Json.Serialization;

namespace UI.Models
{
    public class Package
    {
        [JsonPropertyName("id")]
        public int Id { get; set; }

        [JsonPropertyName("createdOn")]
        public DateTime CreatedOn { get; set; }

        [JsonPropertyName("deliveryAddress")]
        public string DeliveryAddress { get; set; }

        [JsonPropertyName("phoneNumber")]
        public string PhoneNumber { get; set; }

        [JsonPropertyName("payOnDelivery")]
        public bool PayOnDelivery { get; set; }

        [JsonPropertyName("status")]
        public string Status { get; set; }
    }
}
