using CommunityToolkit.Mvvm.ComponentModel;
using System.Collections.ObjectModel;
using UI.Models;
using UI.Services;

namespace UI.ViewModel
{
    public partial class IndexViewModel : ObservableObject
    {
        [ObservableProperty]
        public ObservableCollection<Package> packages;

        private readonly PackageService packageService;
        public IndexViewModel(PackageService packageService) 
        {
            this.packageService = packageService;
            LoadPackages();
        }

        public async Task LoadPackages()
        {
            Packages = new ObservableCollection<Package>(await packageService.GetPackages());
        }
    }
}
