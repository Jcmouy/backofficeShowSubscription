import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICuenta, Cuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from './cuenta.service';
import { CuentaComponent } from './cuenta.component';
import { CuentaDetailComponent } from './cuenta-detail.component';
import { CuentaUpdateComponent } from './cuenta-update.component';

@Injectable({ providedIn: 'root' })
export class CuentaResolve implements Resolve<ICuenta> {
  constructor(private service: CuentaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICuenta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cuenta: HttpResponse<Cuenta>) => {
          if (cuenta.body) {
            return of(cuenta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Cuenta());
  }
}

export const cuentaRoute: Routes = [
  {
    path: '',
    component: CuentaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.cuenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CuentaDetailComponent,
    resolve: {
      cuenta: CuentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.cuenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CuentaUpdateComponent,
    resolve: {
      cuenta: CuentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.cuenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CuentaUpdateComponent,
    resolve: {
      cuenta: CuentaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.cuenta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
