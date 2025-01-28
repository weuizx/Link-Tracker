package edu.java.scrapper.controllers.dto;

import edu.java.scrapper.repositories.jpa.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(
    componentModel = "spring",
    uses = {LinkMapper.class},
    unmappedTargetPolicy =  ReportingPolicy.ERROR,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public abstract class ClientMapper {

    public abstract ClientDtoOut clientToClientDtoOut(Client client);

}
