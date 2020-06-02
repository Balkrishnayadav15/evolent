import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  id: number;
  user: User;
  submitted = false;
  updateUserForm: FormGroup;
  errorMessage:string;
  isError = false;
  status:string;
  userStatus = false;
  constructor(private route: ActivatedRoute,private router: Router,
    private userService: UserService,private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.user = new User();

    this.id = this.route.snapshot.params['id'];

      this.updateUserForm = this.formBuilder.group({
        firstName: ['', [Validators.required,Validators.maxLength(50),Validators.pattern('^[a-zA-Z ]*$')]],
        lastName: ['', [Validators.required,Validators.maxLength(50),Validators.pattern('^[a-zA-Z ]*$')]],
          emailId: ['', [Validators.required, Validators.email]],
          mobileNumber: ['', [Validators.required, Validators.minLength(10),Validators.pattern('^[0-9]*$')]],
          status:['']
      });

    this.userService.getUser(this.id)
      .subscribe(data => {
        this.user = data;
        this.isError =  false;
        this.errorMessage = '';
        if(this.user.status == "Active"){
          this.userStatus = true;
        }else{
          this.userStatus = false;
        }
        this.updateUserForm.patchValue({
          firstName: this.user.firstName,
          lastName: this.user.lastName,
          emailId :this.user.emailId,
          mobileNumber :this.user.mobileNumber,
          status : this.userStatus
        });
       
      }, error => {
      });
  }

  get f() { return this.updateUserForm.controls; }

  updateUser() {
    this.user = this.updateUserForm.value;
    this.user.status = this.status;
    this.userService.updateUser(this.id, this.user)
      .subscribe(
        data => {
          console.log(data);
          this.user = new User();
          this.gotoList();
        }, 
        error => {
          this.isError =  true;
          this.errorMessage = error.error.message;
        }
        );
   
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.updateUserForm.invalid) {
        return;
    }
    this.updateUser();    
  }

  gotoList() {
    this.router.navigate(['/users']);
  }

  updateStatus(e){
    
    if(e.target.checked){
      this.status= "Active";
    }else{
      this.status = "Inactive";
    }
  }
}
