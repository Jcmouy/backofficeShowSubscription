import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMoneda, Moneda } from 'app/shared/model/moneda.model';
import { MonedaService } from './moneda.service';
import { MonedaComponent } from './moneda.component';
import { MonedaDetailComponent } from './moneda-detail.component';
import { MonedaUpdateComponent } from './moneda-update.component';

@Injectable({ providedIn: 'root' })
export class MonedaResolve implements Resolve<IMoneda> {
  constructor(private service: MonedaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMoneda> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((moneda: HttpResponse<Moneda>) => {
          if (moneda.body) {
            return of(moneda.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Moneda());
  }
}

export const monedaRoute: Routes = [
  {
    path: '',
    component: MonedaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.moneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MonedaDetailComponent,
    resolve: {
      moneda: MonedaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.moneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MonedaUpdateComponent,
    resolve: {
      moneda: MonedaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.moneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MonedaUpdateComponent,
    resolve: {
      moneda: MonedaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.moneda.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
