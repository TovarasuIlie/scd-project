using System.Text.Json.Serialization;

namespace UI.Models
{
    public class QRCode
    {
        [JsonPropertyName("id")]
        public int ID { get; set; }
        [JsonPropertyName("image")]
        public string Image { get; set; }
    }
}
