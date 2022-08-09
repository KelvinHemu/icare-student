import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import {
  AddUserComponent,
  UserManagementComponent,
  MaintenanceHomeComponent,
  LocationManagementComponent,
  EditUserComponent,
  PriceListHomeComponent,
} from "./pages";
import { PrivilegesAndRolesComponent } from "./pages/privileges-and-roles/privileges-and-roles.component";
import { SystemSettingsComponent } from "./pages/system-settings/system-settings.component";

// TODO: Improve routing, at least include child routing
const routes: Routes = [
  {
    path: "",
    component: MaintenanceHomeComponent,
    children: [
      // TODO: Need to find best starting page for maintenance
      // {
      //   path: "",
      //   redirectTo: "price-list",
      // },
      {
        path: "price-list",
        component: PriceListHomeComponent,
      },
      {
        path: "price-list/:department",
        component: PriceListHomeComponent,
      },
      {
        path: "users",
        component: UserManagementComponent,
      },
      {
        path: "location",
        component: LocationManagementComponent,
      },
      {
        path: "users/add-user",
        component: AddUserComponent,
      },
      {
        path: "users/edit-user",
        component: EditUserComponent,
      },
      {
        path: "system-settings",
        component: SystemSettingsComponent,
      },
      {
        path: "system-privileges-and-roles",
        component: PrivilegesAndRolesComponent,
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MaintenanceRoutingModule {}
