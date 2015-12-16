package com.amtechventures.tucita.model.context.appointment;

import com.amtechventures.tucita.model.domain.appointment.Appointment;
import com.amtechventures.tucita.model.error.AppError;
import java.util.List;

public class AppointmentCompletion {

    public interface AppointmentErrorCompletion{

        void completion(List<Appointment> appointmentList, AppError error);

    }
}
