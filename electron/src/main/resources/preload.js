const { contextBridge, ipcRenderer } = require('electron')

contextBridge.exposeInMainWorld('watchlistIpc',{
  'save': (data) => ipcRenderer.invoke('save', data),
  'load': () => ipcRenderer.invoke('load')
})
