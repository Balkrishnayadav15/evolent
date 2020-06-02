import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators ,ReactiveFormsModule } from '@angular/forms';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent implements OnInit {

    addUserForm: FormGroup;
    submitted = false;
    user:User = new User();
    errorMessage:string;
    isError = false;
    active = "Active";
    constructor(private userService: UserService, private router: Router,private formBuilder: FormBuilder) { }

    ngOnInit() {
        this.addUserForm = this.formBuilder.group({
            firstName: ['', [Validators.required,Validators.maxLength(50),Validators.pattern('^[a-zA-Z ]*$')]],
            lastName: ['', [Validators.required,Validators.maxLength(50),Validators.pattern('^[a-zA-Z ]*$')]],
            emailId: ['', [Validators.required, Validators.email]],
            mobileNumber: ['', [Validators.required, Validators.minLength(10),Validators.pattern('^[0-9]*$')]],
            status:['Active']
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.addUserForm.controls; }

    newUser(): void {
      this.submitted = false;
      this.user = new User();
    }
    save() {
      this.userService.createUser(this.user)
        .subscribe(
          response => {
            if(response.status === 200){
              this.user = new User();
              this.errorMessage = '';
              this.isError = false;
              this.gotoList();
            }
          }, 
          error => {
            this.isError =  true;
            this.errorMessage = error.error.message;
          });
    }
    onSubmit() {
        this.submitted = true;

        // stop here if form is invalid
        if (this.addUserForm.invalid) {
            return;
        }
       // this.submitted = true;
       this.user = this.addUserForm.value;
       console.log('Your form data : ', this.user );
       this.save(); 
    }
    gotoList() {
      this.router.navigate(['/users']);
    }
}
