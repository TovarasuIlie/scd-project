using System.Text.Json.Serialization;

namespace UI.Models
{
    public class API
    {
        [JsonPropertyName("httpStatus")]
        public string HttpStatus { get; set; }

        [JsonPropertyName("message")]
        public string Message { get; set; }
    }
}
