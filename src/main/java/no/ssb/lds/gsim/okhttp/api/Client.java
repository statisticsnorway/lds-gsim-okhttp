package no.ssb.lds.gsim.okhttp.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ssb.lds.gsim.okhttp.BusinessProcess;
import no.ssb.lds.gsim.okhttp.InstanceVariable;
import no.ssb.lds.gsim.okhttp.LogicalRecord;
import no.ssb.lds.gsim.okhttp.ProcessExecutionLog;
import no.ssb.lds.gsim.okhttp.ProcessStep;
import no.ssb.lds.gsim.okhttp.ProcessStepInstance;
import no.ssb.lds.gsim.okhttp.StatisticalProgram;
import no.ssb.lds.gsim.okhttp.StatisticalProgramCycle;
import no.ssb.lds.gsim.okhttp.TransformableInput;
import no.ssb.lds.gsim.okhttp.TransformedOutput;
import no.ssb.lds.gsim.okhttp.UnitDataStructure;
import no.ssb.lds.gsim.okhttp.UnitDataset;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public class Client extends Configured {

    public CompletableFuture<UnitDataset> fetchUnitDataset(String id, Instant timestamp) {
        UnitDataset.Fetcher fetcher = new UnitDataset.Fetcher();
        fetcher.withParametersFrom(this).withTimestamp(timestamp);
        return fetcher.fetchAsync(id).thenApply(result -> (UnitDataset) result.withParametersFrom(this));
    }

    public CompletableFuture<Void> updateUnitDataset(String id, UnitDataset dataset) throws IOException {
        UnitDataset.Fetcher fetcher = new UnitDataset.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, dataset);
    }

    public CompletableFuture<Void> updateUnitDataStructure(String id, UnitDataStructure dataset) throws IOException {
        UnitDataStructure.Fetcher fetcher = new UnitDataStructure.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, dataset);
    }

    public CompletableFuture<Void> updateLogicalRecord(String id, LogicalRecord logicalRecord) throws IOException {
        LogicalRecord.Fetcher fetcher = new LogicalRecord.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, logicalRecord);
    }

    public CompletableFuture<Void> updateInstanceVariable(String id, InstanceVariable instanceVariable) throws IOException {
        InstanceVariable.Fetcher fetcher = new InstanceVariable.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, instanceVariable);
    }

    public CompletableFuture<Void> updateStatisticalProgram(String id, StatisticalProgram statisticalProgram) throws IOException {
        StatisticalProgram.Fetcher fetcher = new StatisticalProgram.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, statisticalProgram);
    }

    public CompletableFuture<StatisticalProgramCycle> fetchStatisticalProgramCycle(String id) {
        StatisticalProgramCycle.Fetcher fetcher = new StatisticalProgramCycle.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.fetchAsync(id).thenApply(result -> (StatisticalProgramCycle) result.withParametersFrom(this));
    }

    public CompletableFuture<Void> updateStatisticalProgramCycle(String id, StatisticalProgramCycle statisticalProgramCycle) throws IOException {
        StatisticalProgramCycle.Fetcher fetcher = new StatisticalProgramCycle.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, statisticalProgramCycle);
    }

    public CompletableFuture<Void> updateBusinessProcess(String id, BusinessProcess instanceVariable) throws IOException {
        BusinessProcess.Fetcher fetcher = new BusinessProcess.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, instanceVariable);
    }

    public CompletableFuture<Void> updateProcessStep(String id, ProcessStep processStep) throws IOException {
        ProcessStep.Fetcher fetcher = new ProcessStep.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, processStep);
    }

    public CompletableFuture<Void> updateProcessStepInstance(String id, ProcessStepInstance instanceVariable) throws IOException {
        ProcessStepInstance.Fetcher fetcher = new ProcessStepInstance.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, instanceVariable);
    }

    public CompletableFuture<Void> updateProcessExecutionLog(String id, ProcessExecutionLog processExecutionLog) throws IOException {
        ProcessExecutionLog.Fetcher fetcher = new ProcessExecutionLog.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, processExecutionLog);
    }

    public CompletableFuture<Void> updateTransformableInput(String id, TransformableInput instanceVariable) throws IOException {
        TransformableInput.Fetcher fetcher = new TransformableInput.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, instanceVariable);
    }

    public CompletableFuture<Void> updateTransformedOutput(String id, TransformedOutput instanceVariable) throws IOException {
        TransformedOutput.Fetcher fetcher = new TransformedOutput.Fetcher();
        fetcher.withParametersFrom(this);
        return fetcher.updateAsync(id, instanceVariable);
    }

    @Override
    public Client withMapper(ObjectMapper mapper) {
        return (Client) super.withMapper(mapper);
    }

    @Override
    public Client withClient(OkHttpClient client) {
        return (Client) super.withClient(client);
    }

    @Override
    public Client withPrefix(HttpUrl prefix) {
        return (Client) super.withPrefix(prefix);
    }

    @Override
    public Client withTimestamp(Long timestamp) {
        return (Client) super.withTimestamp(timestamp);
    }

    @Override
    public Client withTimestamp(Instant timestamp) {
        return (Client) super.withTimestamp(timestamp);
    }

    @Override
    public Client withParametersFrom(Configured configured) {
        return (Client) super.withParametersFrom(configured);
    }
}
