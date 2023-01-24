package com.springapllication.Application1.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springapllication.Application1.Model.Folder;
import com.springapllication.Application1.Repository.DFolderRepository;
import com.springapllication.Application1.Repository.StudentRepository;

@Service
public class Services {
	@Autowired
	private StudentRepository Srepo;
	@Autowired
	private DFolderRepository Frepo;
	public List<Folder> getallFolders(){
		return Frepo.findAll();
	}
}
