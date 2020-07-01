package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoleService;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	UserSecurityService userSecurityService;

	@Autowired
	HotelService hotelService;

	@GetMapping("/AllOwners")
	public ResponseEntity<List<User>> hotelOwners(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok( userService.findAllHotelOwners(page, size));
	}

	@GetMapping("/SeeHotelOwner/{username}")
	public ResponseEntity<User> getHotelOwner(@PathVariable("username")String username){
		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()){
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PatchMapping("/EditUser")
	public ResponseEntity<User> updatedUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.updateUser(user.getUserId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getRoles()));
	}

	@PutMapping("/EditRole")
	public ResponseEntity<User> updatedRole(@RequestBody User user) {
		return ResponseEntity.ok(userService.updateRole(user.getUserId(), user.getRoles()));
	}

	@GetMapping("/AllHotels")
	public ResponseEntity<Page<Hotel>> allHotels(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok(hotelService.findAll(page,size));
	}

	@GetMapping("/AllUsers")
	public ResponseEntity<Page<User>> allUsers(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok(userService.findAll(page, size));
	}

	@GetMapping("/AllRoles")
	public ResponseEntity<List<Role>> allRoles(){
		return ResponseEntity.ok(roleService.findAll());
	}


}
