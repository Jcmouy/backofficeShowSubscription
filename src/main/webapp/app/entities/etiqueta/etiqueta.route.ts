import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEtiqueta, Etiqueta } from 'app/shared/model/etiqueta.model';
import { EtiquetaService } from './etiqueta.service';
import { EtiquetaComponent } from './etiqueta.component';
import { EtiquetaDetailComponent } from './etiqueta-detail.component';
import { EtiquetaUpdateComponent } from './etiqueta-update.component';

@Injectable({ providedIn: 'root' })
export class EtiquetaResolve implements Resolve<IEtiqueta> {
  constructor(private service: EtiquetaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEtiqueta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((etiqueta: HttpResponse<Etiqueta>) => {
          if (etiqueta.body) {
            return of(etiqueta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Etiqueta());
  }
}

export const etiquetaRoute: Routes = [
  {
    path: '',
    component: EtiquetaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.etiqueta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EtiquetaDetailComponent,
    resolve: {
      etiqueta: EtiquetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.etiqueta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EtiquetaUpdateComponent,
    resolve: {
      etiqueta: EtiquetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.etiqueta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EtiquetaUpdateComponent,
    resolve: {
      etiqueta: EtiquetaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.etiqueta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
