package com.example.batch.job1.chunk1;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class Job1ChunkListener implements ChunkListener {

	@Override
	public void beforeChunk(ChunkContext context) {
    	System.out.println("Job1ChunkListener beforeChunk");
	}

	@Override
	public void afterChunk(ChunkContext context) {
    	System.out.println("Job1ChunkListener afterChunk");
	}

	@Override
	public void afterChunkError(ChunkContext context) {
    	System.out.println("Job1ChunkListener afterChunkError");
	}

}
