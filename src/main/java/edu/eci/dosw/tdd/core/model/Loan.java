package edu.eci.dosw.tdd.core.model;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Data
@Setter
@Getter

public class Loan {
    private Book book;
    private User user;
    private Date loanDate;
    private Status status;
    private Date returnDate;
}
