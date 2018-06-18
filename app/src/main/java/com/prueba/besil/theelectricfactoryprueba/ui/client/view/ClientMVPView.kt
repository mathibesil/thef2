package com.prueba.besil.theelectricfactoryprueba.ui.client.view

import com.prueba.besil.theelectricfactoryprueba.data.network.DTO.ClientDTO
import com.prueba.besil.theelectricfactoryprueba.ui.base.view.MVPView

interface ClientMVPView : MVPView {
    fun updateClients(listClients: List<ClientDTO>)
    fun swipeRefreshOff()
    fun loadProgress(enabled: Boolean)
    fun itemClicked(client: ClientDTO)
}