package com.springapllication.Application1.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springapllication.Application1.Model.Folder;
import com.springapllication.Application1.Model.Student;
//import com.Harsha.Application.Model.Student;
import com.springapllication.Application1.Repository.DFolderRepository;
import com.springapllication.Application1.Repository.StudentRepository;
import com.springapllication.Application1.Service.Services;
@RestController
@Component
@RequestMapping("/api")
public class StudentController {
	@Autowired
	private Services services;
	@Autowired
	private StudentRepository Srepo;
	@Autowired
	private DFolderRepository Frepo;
//	@PostMapping("/{fid}/fileName")
//	public ResponseEntity<String> createFile(@RequestBody Student std, @PathVariable Long fid) {	
//		Student std1=new Student(std.getPerson(), fid,std.getFName(),std.getFtype(),std.getFile());
//		Srepo.save(std1);
//		return ResponseEntity.ok("created");
//	}
	@PostMapping("/{fid}/{person}/fileName")
	public ResponseEntity<String> createFile(@RequestParam("file1") MultipartFile file, @PathVariable Long fid ,@PathVariable String person)throws IOException {	
		List<Student> s1=Srepo.findAll();
		for(Student std:s1) {
			if(std.getFName().equals(file.getOriginalFilename()) && std.getFolderNamee()==fid) {
				return ResponseEntity.ok("exiting file");
			}
		}
		Student std1=new Student(person, fid,file.getOriginalFilename(),file.getContentType(),file.getBytes());
		Srepo.save(std1);
		return ResponseEntity.ok("created");
	}
	@PostMapping("/folderName/{folderName1}")
	public ResponseEntity<String> createFolder(@PathVariable String folderName1){
		
		String name=folderName1;
		List<Folder> f1=Frepo.findAll();
		for(Folder f2:f1) {
			if(f2.getFolderName().equals(name)) {
				return ResponseEntity.ok("exiting folder");
			}
		}
		Folder fld=new Folder(name);
		Frepo.save(fld);
		return ResponseEntity.ok("created");
	}
	@PutMapping("/{fid}/{folderNamenew}")
	public ResponseEntity<String> updateFileName(@PathVariable Long fid,@PathVariable String folderNamenew){
		@SuppressWarnings("deprecation")
		Student f=Srepo.getById(fid);
		f.setFName(folderNamenew);
		Srepo.save(f);
		return ResponseEntity.ok("updated");
	}
//	@PostMapping("/folderName")
//	public ResponseEntity<String> createFolder(@RequestParam("folderName") MultipartFile file)throws IOException{
//		System.out.println("dcdk");
//		Folder fld=new Folder(file.getOriginalFilename(),file.getBytes());
//		Frepo.save(fld);
//		return ResponseEntity.ok("created");
//	}
	@GetMapping("/folders")
	public List<Folder> getfolder() {
		return services.getallFolders();
	}
	@GetMapping("/{fid}")
	public ArrayList<Student> getFiles(@PathVariable Long fid){
		ArrayList<Student> cars = new ArrayList<Student>();
		List<Student> std=Srepo.findAll();
		for(Student std1:std) {
			if (std1.getFolderNamee()==fid){
				cars.add(std1);
			}
		}
		
		return cars;
	}
	@GetMapping("{fid}/{id}")
	public Student getFolder(@PathVariable Long fid,@PathVariable Long id){
		List<Student> std =Srepo.findAll();
		for(Student std1:std) {
			if(std1.getFolderNamee()==fid && std1.getId()==id) {
				return std1;
			}
		}
		return null;
	}
	@DeleteMapping("/delete/{fid}")
	public ResponseEntity<String> deleteFolder(@PathVariable Long fid){
		Frepo.deleteById(fid);
		List<Student> std =Srepo.findAll();
		for(Student std1:std) {
			if(std1.getFolderNamee()==fid) {
				Srepo.deleteById(std1.getId());
			}
		}
		return ResponseEntity.ok("Deleted");
	}
	@DeleteMapping("/delete/{fid}/{fileid}")
	public ResponseEntity<String> deleteFolder(@PathVariable Long fid,@PathVariable Long fileid){
		Srepo.deleteById(fileid);
		return ResponseEntity.ok("deleted");
	}
	@PutMapping("/updateFolder1/{fid}/{folderName}")
	public ResponseEntity<String> updateFolder(@PathVariable Long fid,@PathVariable String folderName) throws IOException{
		List<Folder> fld=Frepo.findAll();
		for(Folder fd:fld) {
			if (fd.getId()==fid) {
				fd.setFolderName(folderName);
				return ResponseEntity.ok("updated folder");
			}
		}
		return ResponseEntity.ok(null);
	}
//	@PutMapping("/{folderid}/{folderName}")
//	public ResponseEntity<String> updateFolder(@PathVariable Long folderid,@PathVariable String folderName) throws IOException {
//		Folder fp=Frepo.getById(folderid);
//		fp.setFolderName(folderName);
//		Frepo.save(fp);
//		return ResponseEntity.ok("Updated");
//	}
//	@PutMapping("/updateFile/{folderid}/{fileid}/{fileName}")
//	public ResponseEntity<String> updateFile(@RequestBody Student fl,@PathVariable Long folderid,@PathVariable Long fileid,@PathVariable String fileName) {
//		@SuppressWarnings("deprecation")
//		Student std =Srepo.getById(fileid);
//		std.setFName(fileName);
//		Srepo.save(std);
//		return ResponseEntity.ok("Updated");
//	}
	
	
}
