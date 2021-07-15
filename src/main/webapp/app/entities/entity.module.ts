import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'obra',
        loadChildren: () => import('./obra/obra.module').then(m => m.PlataformaBackofficeObraModule),
      },
      {
        path: 'tipo-de-obra',
        loadChildren: () => import('./tipo-de-obra/tipo-de-obra.module').then(m => m.PlataformaBackofficeTipoDeObraModule),
      },
      {
        path: 'etiqueta',
        loadChildren: () => import('./etiqueta/etiqueta.module').then(m => m.PlataformaBackofficeEtiquetaModule),
      },
      {
        path: 'contenido',
        loadChildren: () => import('./contenido/contenido.module').then(m => m.PlataformaBackofficeContenidoModule),
      },
      {
        path: 'proceso',
        loadChildren: () => import('./proceso/proceso.module').then(m => m.PlataformaBackofficeProcesoModule),
      },
      {
        path: 'funcion',
        loadChildren: () => import('./funcion/funcion.module').then(m => m.PlataformaBackofficeFuncionModule),
      },
      {
        path: 'moneda',
        loadChildren: () => import('./moneda/moneda.module').then(m => m.PlataformaBackofficeMonedaModule),
      },
      {
        path: 'pais',
        loadChildren: () => import('./pais/pais.module').then(m => m.PlataformaBackofficePaisModule),
      },
      {
        path: 'cuenta',
        loadChildren: () => import('./cuenta/cuenta.module').then(m => m.PlataformaBackofficeCuentaModule),
      },
      {
        path: 'persona',
        loadChildren: () => import('./persona/persona.module').then(m => m.PlataformaBackofficePersonaModule),
      },
      {
        path: 'pago',
        loadChildren: () => import('./pago/pago.module').then(m => m.PlataformaBackofficePagoModule),
      },
      {
        path: 'subscripcion',
        loadChildren: () => import('./subscripcion/subscripcion.module').then(m => m.PlataformaBackofficeSubscripcionModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class PlataformaBackofficeEntityModule {}
