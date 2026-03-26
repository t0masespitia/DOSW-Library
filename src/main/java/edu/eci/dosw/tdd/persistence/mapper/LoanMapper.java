package edu.eci.dosw.tdd.persistence.mapper;

import edu.eci.dosw.tdd.controller.dto.LoanDTO;
import edu.eci.dosw.tdd.core.model.Loan;
import edu.eci.dosw.tdd.core.model.Status;
import edu.eci.dosw.tdd.persistence.entity.LoanEntity;
import edu.eci.dosw.tdd.persistence.entity.LoanStatus;

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
                null,
                loan.getUser().getId(),
                loan.getBook().getId(),
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
}