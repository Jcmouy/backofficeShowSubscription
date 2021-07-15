import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoDeObra, TipoDeObra } from 'app/shared/model/tipo-de-obra.model';
import { TipoDeObraService } from './tipo-de-obra.service';
import { TipoDeObraComponent } from './tipo-de-obra.component';
import { TipoDeObraDetailComponent } from './tipo-de-obra-detail.component';
import { TipoDeObraUpdateComponent } from './tipo-de-obra-update.component';

@Injectable({ providedIn: 'root' })
export class TipoDeObraResolve implements Resolve<ITipoDeObra> {
  constructor(private service: TipoDeObraService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoDeObra> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoDeObra: HttpResponse<TipoDeObra>) => {
          if (tipoDeObra.body) {
            return of(tipoDeObra.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoDeObra());
  }
}

export const tipoDeObraRoute: Routes = [
  {
    path: '',
    component: TipoDeObraComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.tipoDeObra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoDeObraDetailComponent,
    resolve: {
      tipoDeObra: TipoDeObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.tipoDeObra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoDeObraUpdateComponent,
    resolve: {
      tipoDeObra: TipoDeObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.tipoDeObra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoDeObraUpdateComponent,
    resolve: {
      tipoDeObra: TipoDeObraResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.tipoDeObra.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
