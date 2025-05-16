using System.ComponentModel.DataAnnotations;

namespace UI.Models.FormModels
{
    public class OTPVetifyForm
    {
        [Required]
        public string Code { get; set; }
    }
}
