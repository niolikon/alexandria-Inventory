package org.niolikon.alexandria.inventory.stock.packet.converter;

import org.niolikon.alexandria.inventory.catalog.commons.converter.ProductToProductViewConverter;
import org.niolikon.alexandria.inventory.stock.packet.dto.PacketView;
import org.niolikon.alexandria.inventory.stock.packet.entities.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PacketToPacketViewConverter  implements Converter<Packet, PacketView> {
	
	@Autowired
	ProductToProductViewConverter productConverter;

    @Override
    public PacketView convert(@NonNull Packet source) {
    	PacketView view = new PacketView();
        view.setId(source.getId());
        view.setLocation(source.getLocation());
        view.setSize(source.getSize());
        view.setWeight(source.getWeight());
        view.setBarcode(source.getBarcode());
        view.setOrderId(source.getOrderId());
        view.setShipmentId(source.getShipmentId());
        view.setProduct( productConverter.convert(source.getProduct()));
        return view;
    }

}
