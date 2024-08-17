package ru.gb.spring.my_timesheet.service;

import ru.gb.spring.my_timesheet.model.Timesheet;

import java.util.Optional;

public class ProxyTimesheetService extends TimesheetService {

    private final TimesheetService original;

    public ProxyTimesheetService(TimesheetService original) {
        super(original);
        this.original = original;
    }

    @Override
    public Optional<Timesheet> findById(Long id) {
        // BEFORE
        Optional<Timesheet> result = null;
        try {
            result = original.findById(id);
            // AFTER RETURNING
        } catch (Throwable e) {
            // AFTER THROWING
            throw e;
        } finally {
            // AFTER
            return result;
        }
    }
}
