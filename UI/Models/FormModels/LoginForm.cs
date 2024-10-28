using System.ComponentModel.DataAnnotations;

namespace UI.Models.FormModels
{
    public class LoginForm
    {
        [Required(ErrorMessage = "Adresa de email este obligatorie!")]
        [EmailAddress(ErrorMessage = "Adresa de email introdusa nu este valabila!")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Parola este obligatorie!")]
        public string Password { get; set; }
    }
}
