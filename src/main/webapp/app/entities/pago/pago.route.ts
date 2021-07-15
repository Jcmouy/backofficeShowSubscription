import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPago, Pago } from 'app/shared/model/pago.model';
import { PagoService } from './pago.service';
import { PagoComponent } from './pago.component';
import { PagoDetailComponent } from './pago-detail.component';
import { PagoUpdateComponent } from './pago-update.component';

@Injectable({ providedIn: 'root' })
export class PagoResolve implements Resolve<IPago> {
  constructor(private service: PagoService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPago> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pago: HttpResponse<Pago>) => {
          if (pago.body) {
            return of(pago.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Pago());
  }
}

export const pagoRoute: Routes = [
  {
    path: '',
    component: PagoComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'plataformaBackofficeApp.pago.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PagoDetailComponent,
    resolve: {
      pago: PagoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.pago.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PagoUpdateComponent,
    resolve: {
      pago: PagoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.pago.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PagoUpdateComponent,
    resolve: {
      pago: PagoResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'plataformaBackofficeApp.pago.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
