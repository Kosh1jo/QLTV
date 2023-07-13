package com.example.QLTV.Convert;

import com.example.QLTV.Dto.ReaderDto;
import com.example.QLTV.Entity.Reader;
import org.springframework.stereotype.Component;

@Component
public class ReaderConvert {
    public ReaderDto toDTO(Reader reader){
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(reader.getId());
        readerDto.setName(reader.getName());
        readerDto.setEmail(reader.getEmail());
        readerDto.setPassword(reader.getPassword());
        readerDto.setRoles(reader.getRoles());
        readerDto.setPhone(reader.getPhone());
        readerDto.setActive(reader.isActive());
        return readerDto;
    }
    public Reader toEntity(ReaderDto readerDto){
        Reader reader = new Reader();
        reader.setPassword(readerDto.getPassword());
        reader.setName(readerDto.getName());
        reader.setEmail(readerDto.getEmail());
        reader.setRoles(readerDto.getRoles());
        reader.setPhone(readerDto.getPhone());
        reader.setActive(readerDto.isActive());
        return reader;
    }

}
