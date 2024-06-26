package com.example.sampleapp.demo.entity.mapper;

import com.example.sampleapp.demo.entity.database.Profile;
import com.example.sampleapp.demo.entity.request.CreateProfileRequest;
import com.example.sampleapp.demo.entity.response.CreateProfileResponse;
import com.example.sampleapp.demo.entity.response.FindProfileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfileMapper {
    Profile mapEntity(CreateProfileRequest request);

    @Mapping(target = "userId", source = "user.id")
    CreateProfileResponse mapResponse(Profile profile);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "userName", source = "user.name")
    FindProfileResponse mapFindResponse(Profile profile);
}
