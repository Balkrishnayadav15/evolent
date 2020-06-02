import { Component, OnInit } from '@angular/core';
import { User } from '../user';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: User[] = [];
  isDeletd: boolean = false;
  deletedUser : string;
  constructor(private userService: UserService,
    private router: Router) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.userService.getUserList().subscribe(
      data => {
        this.users = data;
        
      }
    );
    
  }

  deleteUser(id: number, user:User) {
    this.userService.deleteUser(id)
      .subscribe(
        data => {
          this.deletedUser = user.firstName +' '+ user.lastName;
          this.isDeletd = data.deleted;
          this.reloadData();
        },
        error => console.log(error));
  }

  userDetails(id: number){
    this.router.navigate(['details', id]);
  }

  updateUser(id: number){
    this.router.navigate(['update', id]);
  }

}
