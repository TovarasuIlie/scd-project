using System.ComponentModel.DataAnnotations;

namespace UI.Models.FormModels
{
    public class RegisterForm
    {
        [Required(ErrorMessage = "Numele este obligatoriu!")]
        public string Name { get; set; }

        [Required(ErrorMessage = "Adresa de email este obligatorie!")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Parola este obligatorie!")]
        [StringLength(255, MinimumLength = 6, ErrorMessage = "Parola trebuie sa contina intre 6 si 255 de caractere!")]
        public string Password { get; set; }

        [Required(ErrorMessage = "Confirmarea parolei este obligatorie!")]
        [Compare(nameof(Password), ErrorMessage = "Cele 2 parole nu se potrivesc!")]
        public string ConfirmPassword { get; set; }
    }
}
