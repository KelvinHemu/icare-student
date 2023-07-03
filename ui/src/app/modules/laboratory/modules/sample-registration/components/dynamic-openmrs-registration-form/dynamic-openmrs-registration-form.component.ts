import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { Store } from "@ngrx/store";
import { Observable } from "rxjs";
import { loadCustomOpenMRSForms } from "src/app/store/actions";
import { AppState } from "src/app/store/reducers";
import { getCustomOpenMRSFormById } from "src/app/store/selectors/form.selectors";

@Component({
  selector: "app-dynamic-openmrs-registration-form",
  templateUrl: "./dynamic-openmrs-registration-form.component.html",
  styleUrls: ["./dynamic-openmrs-registration-form.component.scss"],
})
export class DynamicOpenmrsRegistrationFormComponent implements OnInit {
  @Input() formUuids: string[];
  @Output() formUpdate: EventEmitter<any> = new EventEmitter<any>();
  constructor(private store: Store<AppState>) {}

  ngOnInit(): void {
    this.store.dispatch(
      loadCustomOpenMRSForms({
        formUuids: this.formUuids,
      })
    );
  }

  onFormUpdate(data: any): void {
    this.formUpdate.emit(data);
  }
}
