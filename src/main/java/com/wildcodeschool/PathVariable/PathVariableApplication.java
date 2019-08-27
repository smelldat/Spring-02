package com.wildcodeschool.PathVariable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@SpringBootApplication
public class PathVariableApplication {

	public static void main(String[] args) {
		SpringApplication.run(PathVariableApplication.class, args);
	}

	@RequestMapping("/myDoctor/{number}")
	@ResponseBody
	public Doctor myDoctor(@PathVariable String number, @RequestParam(required = false) Optional details) {

		Integer newNum = Integer.parseInt(number);

		if(newNum < 9) {
			throw new ResponseStatusException(HttpStatus.SEE_OTHER, "Je ne peux pas récupérer ces docteurs");
		}
		if(newNum > 13) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Impossible de récupérer l'incarnation " + number);
		}

		int index = newNum - 9;

		Doctor optionnal = null;

		List<attributeDoctor> optionnalDoctors = new ArrayList<>();
		optionnalDoctors.add(new attributeDoctor("9", "Christopher Eccleston", "13", 41));
		optionnalDoctors.add(new attributeDoctor("10", "David Tennant", "47", 34));
		optionnalDoctors.add(new attributeDoctor("11", "Matt Smith", "44", 27));
		optionnalDoctors.add(new attributeDoctor("12", "Peter Capaldi", "40", 55));
		optionnalDoctors.add(new attributeDoctor("13", "Jodie Whittaker", "11", 35));

		List<Doctor> allDoctors = new ArrayList<>();
		allDoctors.add(new Doctor("9", "Christopher Eccleston"));
		allDoctors.add(new Doctor("10", "David Tennant"));
		allDoctors.add(new Doctor("11", "Matt Smith"));
		allDoctors.add(new Doctor("12", "Peter Capaldi"));
		allDoctors.add(new Doctor("13", "Jodie Whittaker"));

		if (details.isPresent()) {
			optionnal = optionnalDoctors.get(index);
		} else {
			optionnal = allDoctors.get(index);
		}

		return optionnal;
	}

	static class Doctor {

		private String name;
		private String number;


		Doctor(String number, String name) {
			this.name = name;
			this.number = number;
		}

		public String getName() {
			return name;
		}

		public String getNumber() {
			return number;
		}
	}

	static class attributeDoctor extends Doctor {

		private String numberOfEpisodes;
		private Integer age;

			public attributeDoctor (String number, String name, String numberOfEpisodes, Integer age) {
			super(name, number);
			this.numberOfEpisodes = numberOfEpisodes;
			this.age = age;
		}

		public String getNumberOfEpisodes() {
			return numberOfEpisodes;
		}
		public Integer getAge() {
			return age;
		}

	}
}
