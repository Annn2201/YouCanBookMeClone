package com.hive.ycbm.services.impl;

import com.hive.ycbm.dto.BookingPageDto;
import com.hive.ycbm.exception.IDNotFoundException;
import com.hive.ycbm.models.BookingPage;
import com.hive.ycbm.repositories.BookingPageRepository;
import com.hive.ycbm.services.BookingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingPageServiceImpl implements BookingPageService {
    @Autowired
    private BookingPageRepository bookingPageRepository;
    @Override
    public List<BookingPageDto> getAllBookingPage() {
        List<BookingPage> bookingPages = bookingPageRepository.findAll();
        return bookingPages.stream().map(bookingPage -> BookingPageDto.builder()
                .pageId(bookingPage.getPageId())
                .title(bookingPage.getTitle())
                .bookingLink(bookingPage.getBookingLink())
                .build()).collect(Collectors.toList());
    }


    @Override
    public BookingPageDto findById(Long pageId) {
        BookingPage bookingPage = bookingPageRepository.findById(pageId)
                .orElseThrow(() -> new IDNotFoundException("Booking page not found with id: " + pageId));
        return BookingPageDto.builder()
                .pageId(bookingPage.getPageId())
                .title(bookingPage.getTitle())
                .bookingLink(bookingPage.getBookingLink())
                .build();
    }

    @Override
    public void saveBookingPage(BookingPage bookingPage) {
        this.bookingPageRepository.save(bookingPage);
    }

    @Override
    public void updateBookingPage(BookingPageDto bookingPageDto) {
        BookingPage updateBookingPage = bookingPageRepository.findById(bookingPageDto.getPageId()).orElseThrow(() -> new IDNotFoundException("Booking page not found with id: " + bookingPageDto.getPageId()));
        updateBookingPage.setTitle(bookingPageDto.getTitle());
        updateBookingPage.setBookingLink(bookingPageDto.getBookingLink());
        bookingPageRepository.save(updateBookingPage);
    }


    @Override
    public void deleteBookingPage(Long pageId) {
        this.bookingPageRepository.deleteById(pageId);
    }
}
