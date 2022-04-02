package com.settld.fileanalyser;

import com.settld.fileanalyser.exception.AnalysisOrchestratorException;
import com.settld.fileanalyser.exception.ValidationException;
import com.settld.fileanalyser.service.AnalysisOrchestratorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
@AllArgsConstructor
public class FileanalyserApplication implements CommandLineRunner {

	private final AnalysisOrchestratorService analysisOrchestratorService;

	public static void main(String[] args) {

		log.info("Starting the console app...");
		SpringApplication.run(FileanalyserApplication.class, args);
	}


	@Override
	public void run(String... args) {

		try {
			analysisOrchestratorService.initiateDocumentAnalysis(args[0]);
		} catch (AnalysisOrchestratorException e) {
			log.error("Error encountered while reading the arguments");
		} catch (IOException | ValidationException e) {
			e.printStackTrace();
		}


	}
}
