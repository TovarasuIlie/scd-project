using System.ComponentModel.DataAnnotations;

namespace UI.DTOs
{
    public class LoggedUserDTO
    {
        public int? Id { get; set; }
        public string? Email { get; set; }
        public string? Name { get; set; }
        public string? Jwt {  get; set; }

        public static explicit operator LoggedUserDTO(Task<string?> v)
        {
            throw new NotImplementedException();
        }
    }
}
