package edu.eci.dosw.tdd.persistence.relational.mapper;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.persistence.relational.entity.LoanEntity;
import edu.eci.dosw.tdd.persistence.relational.entity.LoanStatus;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static Loan toModel(LoanEntity entity) {
        Loan loan = new Loan();
        loan.setBook(BookMapper.toModel(entity.getBook()));
        loan.setUser(UserMapper.toModel(entity.getUser()));
        loan.setLoanDate(entity.getLoanDate());
        loan.setReturnDate(entity.getReturnDate());
        loan.setStatus(entity.getStatus() == LoanStatus.ACTIVO ? Status.ACTIVE : Status.RETURNED);
        return loan;
    }

    public static LoanDTO toDto(Loan loan) {
        return new LoanDTO(
                loan.getId(),
                loan.getUser() != null ? loan.getUser().getId() : null,
                loan.getBook() != null ? loan.getBook().getId() : null,
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.getStatus() == Status.ACTIVE ? LoanStatus.ACTIVO : LoanStatus.DEVUELTO
        );
    }

    public static LoanDTO toDto(LoanEntity entity) {
        return new LoanDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getBook().getId(),
                entity.getLoanDate(),
                entity.getReturnDate(),
                entity.getStatus()
        );
    }

    public static LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.getId());
        entity.setLoanDate(loan.getLoanDate());
        entity.setReturnDate(loan.getReturnDate());
        entity.setStatus(loan.getStatus() == Status.ACTIVE ? LoanStatus.ACTIVO : LoanStatus.DEVUELTO);
        return entity;
    }

}