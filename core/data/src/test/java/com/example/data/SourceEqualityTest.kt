package com.example.data

import com.example.data.model.Source
import org.junit.Assert.assertEquals
import org.junit.Test

class SourceEqualityTest {

    @Test
    fun `같은_값을_가진_Source_객체는_Set에서_중복되지_않는다`() {
        // Given
        val source1 = Source("test-id", "Test Name")
        val source2 = Source("test-id", "Test Name") // 같은 값의 다른 인스턴스
        val source3 = Source("different-id", "Test Name") // 다른 값
        
        val set = mutableSetOf<Source>()
        
        // When
        set.add(source1)
        set.add(source2) // 중복되지 않아야 함
        set.add(source3) // 추가되어야 함
        
        // Then
        assertEquals("Set 크기는 2여야 함 (중복 제거)", 2, set.size)
        assertEquals("source1과 source2는 같아야 함", source1, source2)
        assertEquals("hashCode도 같아야 함", source1.hashCode(), source2.hashCode())
    }
    
    @Test
    fun `null_id를_가진_Source_객체도_올바르게_비교된다`() {
        // Given
        val source1 = Source(null, "Test Name")
        val source2 = Source(null, "Test Name")
        val source3 = Source(null, "Different Name")
        
        val set = mutableSetOf<Source>()
        
        // When
        set.add(source1)
        set.add(source2) // 중복되지 않아야 함
        set.add(source3) // 추가되어야 함
        
        // Then
        assertEquals("Set 크기는 2여야 함", 2, set.size)
        assertEquals("null id도 올바르게 비교되어야 함", source1, source2)
    }
}