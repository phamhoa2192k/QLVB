package edu.hust.document.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class BasicErrorController implements ErrorController {
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	    
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
					if(statusCode == HttpStatus.FORBIDDEN.value()) {
						return "error/403";
					}
	        else if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error/404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error/500";
	        }
	    }
	    return "error/500";
	}

	@GetMapping("/404")
	public String get404(){
		return "error/404";
	}

	@GetMapping(value="/403")
	public String get403() {
		return "error/403";
	}
	
	@GetMapping(value="/500")
	public String get500() {
		return "error/500";
	}
}
