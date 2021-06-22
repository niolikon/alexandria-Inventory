package org.niolikon.alexandria.inventory.stock.packet;

import javax.validation.Valid;

import org.niolikon.alexandria.inventory.stock.packet.dto.PacketRequest;
import org.niolikon.alexandria.inventory.stock.packet.dto.PacketView;
import org.niolikon.alexandria.inventory.stock.packet.entities.Packet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/packets")
@Api(tags="Management of Packet entities")
public class PacketController {
    
    private final PacketService service;
    
    public PacketController(PacketService service) {
        this.service = service;
    }
    
    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(
            value = "Read packet by ID", notes = "Returns Packet data in JSON", response = PacketView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Packet has been fetched"),
            @ApiResponse(code = 404, message = "Could not find the specified Packet")})
    public PacketView getPacket(@ApiParam("The ID of the Packet") @PathVariable Long id) {
        return service.getPacket(id);
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(
            value = "Read all packets", notes = "Returns Packet data in JSON", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Packets have been fetched"),
            @ApiResponse(code = 404, message = "No Packets are present in the repository")})
    public Page<PacketView> getAllPackets(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.findAllPackets(pageable);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation(
            value = "Create a packet", notes = "Stores the input JSON Packet data", response = PacketView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Packet has been stored"),
            @ApiResponse(code = 409, message = "Could not complete the storage, the input Packet data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public PacketView create(@ApiParam("The input Packet data") @RequestBody @Valid PacketRequest req) {
        return service.create(req);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(
            value = "Delete a packet", notes = "Deletes the specified Packet data", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The Packet has been deleted"),
            @ApiResponse(code = 404, message = "Could not find the specified Packet"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public void deletePacket(@ApiParam("The ID of the Packet") @PathVariable Long id) {
        service.delete(id);
    }
    
    @PutMapping("/{id}")
    @ApiOperation(
            value = "Update a packet", notes = "Modifies the specified Packet with the input JSON Packet data", response = PacketView.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The Packet has been modified"),
            @ApiResponse(code = 404, message = "Could not find the specified Packet"),
            @ApiResponse(code = 409, message = "Could not complete the modification, the input Packet data would cause duplication"),
            @ApiResponse(code = 403, message = "You are not authorized to access this resource"),
            @ApiResponse(code = 401, message = "You are not logged in") })
    public PacketView updatePacket(@ApiParam("The ID of the Packet") @PathVariable Long id,
            @ApiParam("The input Packet data") @RequestBody @Valid PacketRequest req) {
        Packet packet = service.findPacketOrThrow(id);
        return service.update(packet, req);
    }
}
