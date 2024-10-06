using System.ComponentModel.DataAnnotations;

namespace UI.DTOs
{
    public class AuthResponseDTO
    {
        public string? AccessToken { get; set; }
        public string? RefreshToken { get; set; }
        public string? UserName { get; set; }
    }
}
