package project.everyarchive.util.logtracer.logtrace;


import project.everyarchive.util.logtracer.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
