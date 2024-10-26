using UI.ViewModel;

namespace UI.Pages;

public partial class ViewDeliveryDetailsPage : ContentPage
{
	public ViewDeliveryDetailsPage(ViewDeliveryDetailsViewModel viewDeliveryDetailsViewModel)
	{
		InitializeComponent();
		BindingContext = viewDeliveryDetailsViewModel;
	}
}