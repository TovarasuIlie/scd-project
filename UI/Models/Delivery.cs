using System.Text.Json.Serialization;

namespace UI.Models
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

        [JsonPropertyName("createDate")]
        public DateTime CreateDate { get; set; }

        [JsonPropertyName("price")]
        public Decimal Price { get; set; }

    }
}
