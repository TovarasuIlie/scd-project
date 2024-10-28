using System.Text.Json.Serialization;

namespace UI.Models
{
    public class Courier
    {
        [JsonPropertyName("id")]
        public int Id { get; set; }

        [JsonPropertyName("name")]
        public string Name { get; set; }

        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("role")]
        public string Role { get; set; }

        [JsonPropertyName("manager")]
        public Courier Manager { get; set; }

    }
}
