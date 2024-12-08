using System.Text.Json.Serialization;

namespace UI.Models.Authentication
{
    public class LoggedUser
    {
        [JsonPropertyName("id")]
        public int Id { get; set; }

        [JsonPropertyName("email")]
        public string Email { get; set; }

        [JsonPropertyName("name")]
        public string Name { get; set; }

        [JsonPropertyName("role")]
        public string Role { get; set; }

        [JsonPropertyName("jwt")]
        public string Jwt { get; set; }
        public Int32 Expiration { get; set; }
    }
}
