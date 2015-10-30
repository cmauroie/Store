/*Copyright all rights reserved by Carlos Mauricio Idárraga Espitia, this code can’t be use it for business*/
package com.developer.mauricio.store.Provider;


import com.developer.mauricio.store.Models.ItemEntry;

import java.util.List;

/**
 * Created by Mauricio on 27/10/15.
 */
public interface ProviderListener {
    void onResponseEvent(List<ItemEntry> result);
}
