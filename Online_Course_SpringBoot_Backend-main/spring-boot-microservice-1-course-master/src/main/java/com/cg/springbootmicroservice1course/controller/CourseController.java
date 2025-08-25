package com.cg.springbootmicroservice1course.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.cg.springbootmicroservice1course.model.Course;
import com.cg.springbootmicroservice1course.service.CourseService;

@RestController
@RequestMapping("api/course")//pre-path
@Tag(name = "Course", description = "Course management APIs")
public class CourseController
{
    @Autowired
    private CourseService courseService;
    

    @Operation(summary = "Add a new course", description = "Creates a new course with title, subtitle, price, and thumbnail.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Course created successfully"),
        @ApiResponse(responseCode = "500", description = "Failed to upload thumbnail")
    })
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveCourse(
            @Parameter(description = "Course title") @RequestParam String title,
            @Parameter(description = "Course subtitle") @RequestParam String subtitle,
            @Parameter(description = "Course price") @RequestParam Double price,
            @Parameter(description = "Course thumbnail image") @RequestParam("thumbnail") MultipartFile thumbnail
    ) {
        try {
            Course savedCourse = courseService.saveCourse(title, subtitle, price, thumbnail);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload thumbnail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a course", description = "Deletes a course by its ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course deleted successfully")
    })
    @DeleteMapping("{courseId}")//api/course/{courseId}
    public ResponseEntity<?> deleteCourse(@Parameter(description = "ID of the course to delete") @PathVariable Long courseId)
    {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get all courses", description = "Returns a list of all available courses.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of courses returned successfully")
    })
    @GetMapping //api/course
    public ResponseEntity<?> getAllCourses()
    {
        return ResponseEntity.ok(courseService.findAllCourses());
    }
}
